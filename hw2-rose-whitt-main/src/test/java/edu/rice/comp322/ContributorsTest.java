package edu.rice.comp322;
import edu.rice.hj.api.HjMetrics;
import static edu.rice.hj.Module0.abstractMetrics;
import edu.rice.hj.runtime.config.HjSystemProperty;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static edu.rice.hj.Module0.launchHabaneroApp;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

class ContributorsTest{

    private static String username = "rose-whitt"; //TODO: Change to GitHub Username
    private static String token = "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"; //TODO: Change to GitHub Token

    private static ContributorsUI contributorsUI;

    @BeforeAll
    static void setUp(){
        contributorsUI = new ContributorsUI();
    }

    @Test
    public void testEdgeCase() throws IOException {
        String org = "edgecase";
        double reposSize = contributorsUI.loadContributorsSeq(username, token, org);
        List<User> usersSeq = contributorsUI.users;

        HjSystemProperty.abstractMetrics.setProperty(true);
        HjSystemProperty.asyncSpawnCostMetrics.setProperty(1);
        launchHabaneroApp(()->{
            try {
                int reposSizePar = contributorsUI.loadContributorsPar(username, token, org);
                assertTrue("No parallel implementation or repos found", reposSizePar!=0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()->{
            HjMetrics metrics = abstractMetrics();
            final double work = metrics.totalWork();
            List<User> usersPar = contributorsUI.users;
            System.out.println("Test for organization " + org + " achieved total work " + work + " with goal work of " + (reposSize+1));
            assertTrue(reposSize!=0 && work!=0);
            assertEquals(usersSeq.size(), usersPar.size());
            assertTrue("The work (" + work + ") for organization " + org + " is greater than the goal work " + (reposSize + 1), work <= (reposSize+1));
        });
    }

    @Test
    public void testTrabian() throws IOException {
        String org = "trabian";
        double reposSize = contributorsUI.loadContributorsSeq(username, token, org);
        List<User> usersSeq = contributorsUI.users;
        HjSystemProperty.abstractMetrics.setProperty(true);
        HjSystemProperty.asyncSpawnCostMetrics.setProperty(1);
        launchHabaneroApp(()->{
            try {
                int reposSizePar = contributorsUI.loadContributorsPar(username, token, org);
                assertTrue("No parallel implementation or repos found", reposSizePar!=0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()->{
            HjMetrics metrics = abstractMetrics();
            final double work = metrics.totalWork();
            List<User> usersPar = contributorsUI.users;
            System.out.println("Test for organization " + org + " achieved total work " + work + " with goal work of " + (reposSize+1));
            assertTrue(reposSize!=0 && work!=0);
            assertEquals(usersSeq.size(), usersPar.size());
            assertTrue("The work (" + work + ") for organization " + org + " is greater than the goal work " + (reposSize + 1), work <= (reposSize+1));
        });
    }

    @Test
    public void testCollectiveIdea() throws IOException {
        String org = "collectiveidea";
        double reposSize = contributorsUI.loadContributorsSeq(username, token, org);
        List<User> usersSeq = contributorsUI.users;
        HjSystemProperty.abstractMetrics.setProperty(true);
        HjSystemProperty.asyncSpawnCostMetrics.setProperty(1);
        launchHabaneroApp(()->{
            try {
                int reposSizePar = contributorsUI.loadContributorsPar(username, token, org);
                assertTrue("No parallel implementation or repos found", reposSizePar!=0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()->{
            HjMetrics metrics = abstractMetrics();
            final double work = metrics.totalWork();
            List<User> usersPar = contributorsUI.users;
            System.out.println("Test for organization " + org + " achieved total work " + work + " with goal work of " + (reposSize+1));
            assertTrue(reposSize!=0 && work!=0);
            assertEquals(usersSeq.size(), usersPar.size());
            assertTrue("The work (" + work + ") for organization " + org + " is greater than the goal work " + (reposSize + 1), work <= (reposSize+1));
        });
    }
//
    @Test
    public void testGalaxyCats() throws IOException {
        String org = "galaxycats";
        double reposSize = contributorsUI.loadContributorsSeq(username, token, org);
        List<User> usersSeq = contributorsUI.users;
        HjSystemProperty.abstractMetrics.setProperty(true);
        HjSystemProperty.asyncSpawnCostMetrics.setProperty(1);
        launchHabaneroApp(()->{
            try {
                int reposSizePar = contributorsUI.loadContributorsPar(username, token, org);
                assertTrue("No parallel implementation or repos found", reposSizePar!=0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()->{
            HjMetrics metrics = abstractMetrics();
            final double work = metrics.totalWork();
            List<User> usersPar = contributorsUI.users;
            System.out.println("Test for organization " + org + " achieved total work " + work + " with goal work of " + (reposSize+1));
            assertTrue(reposSize!=0 && work!=0);
            assertEquals(usersSeq.size(), usersPar.size());
            assertTrue("The work (" + work + ") for organization " + org + " is greater than the goal work " + (reposSize + 1), work <= (reposSize+1));
        });
    }

    @Test
    public void testRevelation() throws IOException {
        String org = "revelation";
        double reposSize = contributorsUI.loadContributorsSeq(username, token, org);
        List<User> usersSeq = contributorsUI.users;
        HjSystemProperty.abstractMetrics.setProperty(true);
        HjSystemProperty.asyncSpawnCostMetrics.setProperty(1);
        launchHabaneroApp(()->{
            try {
                int reposSizePar = contributorsUI.loadContributorsPar(username, token, org);
                assertTrue("No parallel implementation or repos found", reposSizePar!=0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()->{
            HjMetrics metrics = abstractMetrics();
            final double work = metrics.totalWork();
            List<User> usersPar = contributorsUI.users;
            System.out.println("Test for organization " + org + " achieved total work " + work + " with goal work of " + (reposSize+1));
            assertTrue(reposSize!=0 && work!=0);
            assertEquals(usersSeq.size(), usersPar.size());
            assertTrue("The work (" + work + ") for organization " + org + " is greater than the goal work " + (reposSize + 1), work <= (reposSize+1));
        });
    }

    @Test
    public void testMoneySpyder() throws IOException {
        String org = "moneyspyder";
        double reposSize = contributorsUI.loadContributorsSeq(username, token, org);
        List<User> usersSeq = contributorsUI.users;
        HjSystemProperty.abstractMetrics.setProperty(true);
        HjSystemProperty.asyncSpawnCostMetrics.setProperty(1);
        launchHabaneroApp(()->{
            try {
                int reposSizePar = contributorsUI.loadContributorsPar(username, token, org);
                assertTrue("No parallel implementation or repos found", reposSizePar!=0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()->{
            HjMetrics metrics = abstractMetrics();
            final double work = metrics.totalWork();
            List<User> usersPar = contributorsUI.users;
            System.out.println("Test for organization " + org + " achieved total work " + work + " with goal work of " + (reposSize+1));
            assertTrue(reposSize!=0 && work!=0);
            assertEquals(usersSeq.size(), usersPar.size());
            assertTrue("The work (" + work + ") for organization " + org + " is greater than the goal work " + (reposSize + 1), work <= (reposSize+1));
        });
    }

    @Test
    public void testNotch() throws IOException {
        String org = "notch8";
        double reposSize = contributorsUI.loadContributorsSeq(username, token, org);
        List<User> usersSeq = contributorsUI.users;
        HjSystemProperty.abstractMetrics.setProperty(true);
        HjSystemProperty.asyncSpawnCostMetrics.setProperty(1);
        launchHabaneroApp(()->{
            try {
                int reposSizePar = contributorsUI.loadContributorsPar(username, token, org);
                assertTrue("No parallel implementation or repos found", reposSizePar!=0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, ()->{
            HjMetrics metrics = abstractMetrics();
            final double work = metrics.totalWork();
            List<User> usersPar = contributorsUI.users;
            System.out.println("Test for organization " + org + " achieved total work " + work + " with goal work of " + (reposSize+1));
            assertTrue(reposSize!=0 && work!=0);
            assertEquals(usersSeq.size(), usersPar.size());
            assertTrue("The work (" + work + ") for organization " + org + " is greater than the goal work " + (reposSize + 1), work <= (reposSize+1));
        });
    }
}
