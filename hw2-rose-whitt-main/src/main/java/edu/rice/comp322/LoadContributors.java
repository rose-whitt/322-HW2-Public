package edu.rice.comp322;

import edu.rice.hj.api.HjDataDrivenFuture;
import edu.rice.hj.api.HjFuture;
import edu.rice.hj.api.SuspendableException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

//import static edu.rice.hj.future;
import static edu.rice.hj.Module0.newDataDrivenFuture;
import static edu.rice.hj.Module0.finish;
import static edu.rice.hj.Module0.finish;
import static edu.rice.hj.Module1.*;
import static edu.rice.hj.Module2.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Loads the contributors using the GitHub service.
 */
public interface LoadContributors {

    /**
     * Performs requests to GitHub and loads and aggregates the contributors for all
     * repositories under the given organization.
     */
    default int loadContributorsSeq(String username, String password, String org)
        throws IOException {

        //Create the service to make the requests
        GitHubService service = createGitHubService(username, password);

        //Get all the repos under the given organization
        List<Repo> repos = service.getOrgReposCall(org).execute().body();
        if (repos == null) {
            System.err.println("Error making request to GitHub. Make sure token and organization name are correct.");
            return 0;
        } else if (repos.size() == 0) {
            System.out.println("0 repositories found in " + org + " organization, make sure your token is correct.");
        } else {
            System.out.println("Found " + repos.size() + " repositories in " + org + " organization.");
        }

        int count_none = 0;
        //Get the contributors for each repo
        List<User> users = new ArrayList<>();
        for (Repo repo : repos) {
            List<User> tempUsers = service.getRepContributorsCall(org, repo.name).execute().body();
            if (tempUsers != null) {
                users.addAll(tempUsers);
            } else {
                count_none++;
            }
        }

        List<User> aggregatedUsers = new ArrayList<>();
        for (User user: users) {
            if (aggregatedUsers.contains(user)) {
                aggregatedUsers.get(aggregatedUsers.indexOf(user)).contributions += user.contributions;
            } else {
                aggregatedUsers.add(user);
            }
        }

        //Sort the users in descending order of contributions
        aggregatedUsers.sort((o1, o2) -> o2.contributions - o1.contributions);

        updateContributors(aggregatedUsers);

        return repos.size();
    }

    /**
     * Performs requests to GitHub and loads and aggregates the contributors for all
     * repositories under the given organization in parallel.
     */
    default int loadContributorsPar(String username, String password, String org)
            throws IOException, SuspendableException {
        /**
         * This parallel solution must use futures, data-driven tasks,
         * and streams.
         * All aggregation and post-processing of the results must be
         * performed concurrently using streams.
         */
        //Create the service to make the requests
        GitHubService service = createGitHubService(username, password);

        //Get all the repos under the given organization
        List<Repo> repos = service.getOrgReposCall(org).execute().body();
        if (repos == null) {
            System.err.println("Error making request to GitHub. Make sure token and organization name are correct.");
            return 0;
        } else if (repos.size() == 0) {
            System.out.println("0 repositories found in " + org + " organization, make sure your token is correct.");
        } else {
            System.out.println("Found " + repos.size() + " repositories in " + org + " organization.");
        }

        /**
         * use a for loop
         * make a list of futures, future for each repo, no data driven futures
         */
        List<HjFuture<List<User>>> future_users = new ArrayList<>();
        // for loop to make a future for each repo, add to a list (loop over repos)
        for (Repo repo: repos) {
            var repo_future = future(() -> {
                List<User> tempUsers = null;
                try {
                    // get repo's users
                    tempUsers = service.getRepContributorsCall(org, repo.name).execute().body();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (tempUsers != null) {    // if there are users
                    return tempUsers;   // return the users to get later
                } else {
                    return null;
                }
            });
            future_users.add(repo_future);
        }

        asyncAwait(future_users, () -> {
            List<User> users = new ArrayList<>();

            for (HjFuture<List<User>> futures : future_users) {
                users.addAll(futures.safeGet());
            }

            var aggregatedUsers_test_string = users.stream().parallel()
                    .collect(Collectors.groupingBy(user -> user, Collectors.summingInt(user -> user.contributions)));

            var result = aggregatedUsers_test_string.entrySet().stream().parallel()
                    .map(pair -> new User(pair.getKey().login, pair.getValue()))
                    .sorted((a, b) -> Integer.compare(b.contributions, a.contributions))
                    .collect(Collectors.toList());

            updateContributors(result);
        });

        return repos.size();
    }

    /**
     * Creates the GitHub service with correct authorization.
     */
    default GitHubService createGitHubService(String username, String password) {
        String authToken = "Basic " + new String(Base64.getEncoder().encode((username + ":" + password).getBytes()), UTF_8);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder().header("Accept", "application/vnd.github.v3+json").header("Authorization", authToken);
            Request request = builder.build();
            return chain.proceed(request);
        }  ).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(
            GsonConverterFactory.create()).client(httpClient).build();
        return retrofit.create(GitHubService.class);
    }

    /**
     * Updates the contributors list displayed on the user-interface.
     * @param users a list of Users
     */
    void updateContributors(List<User> users);

}
