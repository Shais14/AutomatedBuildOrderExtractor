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

        for(String id: MatchIds){
            MatchInfo matchInfo = crawler.getMatchInfo(id);
            crawler.downloadReplay(matchInfo);
        }


    }


    public static void main(String[] args) {

        ReplayDownloader replayDownloader = new ReplayDownloader();
        replayDownloader.outFilePath = "/root/AutomatedBuildOrderExtractor/src/outFile.txt";
        Crawler crawler = new Crawler();
        replayDownloader.download(crawler);

    }

}
