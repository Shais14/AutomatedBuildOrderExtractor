package crawler;

import java.util.HashSet;


/**
 * Created by Guest on 12/4/16.
 */
public class ReplayDownloader {


    private HashSet<String> MatchIds;
    private String outFilePath;


    public void download(Crawler crawler) {

        MatchIds = crawler.readSeedFile(outFilePath);
        System.out.println(MatchIds.size());
        for(String id: MatchIds){
            MatchInfo matchInfo = crawler.getMatchInfo(id);
            crawler.downloadReplay(matchInfo);
        }


    }


    public static void main(String[] args) {


        String dir = System.getProperty("user.dir");
        System.out.println("asd");

        ReplayDownloader replayDownloader = new ReplayDownloader();
        replayDownloader.outFilePath = dir + "/outFile.txt";
        System.out.println(replayDownloader.outFilePath);
        Crawler crawler = new Crawler();
        replayDownloader.download(crawler);

    }

}
