//package clarityAttempt;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import skadistats.clarity.model.GameEvent;
//import skadistats.clarity.processor.gameevents.OnGameEvent;
//import skadistats.clarity.processor.runner.Context;
//import skadistats.clarity.processor.runner.SimpleRunner;
//import skadistats.clarity.source.MappedFileSource;
//
///**
// * Created by Anand on 10/10/2016.
// */
//public class Attempt3 {
//    private final Logger log = LoggerFactory.getLogger(Attempt3.class.getPackage().getClass());
//
//    @OnGameEvent
//    public void onGameEvent(Context ctx, GameEvent event) {
//        log.info("{}", event.toString());
//    }
//
//    public void run(String[] args) throws Exception {
//        long tStart = System.currentTimeMillis();
//        new SimpleRunner(new MappedFileSource(args[0])).runWith(this);
//        long tMatch = System.currentTimeMillis() - tStart;
//        log.info("total time taken: {}s", (tMatch) / 1000.0);
//    }
//
//    public static void main(String[] args) throws Exception {
//        new Attempt3().run(args);
//    }
//}
