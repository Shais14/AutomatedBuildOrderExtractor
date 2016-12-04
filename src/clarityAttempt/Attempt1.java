package clarityAttempt;

import com.google.protobuf.GeneratedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skadistats.clarity.Clarity;
import skadistats.clarity.processor.reader.OnMessage;
import skadistats.clarity.processor.reader.OnTickEnd;
import skadistats.clarity.processor.reader.OnTickStart;
import skadistats.clarity.processor.runner.Context;
import skadistats.clarity.processor.runner.ControllableRunner;
import skadistats.clarity.processor.runner.SimpleRunner;
import skadistats.clarity.source.MappedFileSource;
import skadistats.clarity.wire.common.proto.Demo;
import skadistats.clarity.wire.s1.proto.S1NetMessages;
import skadistats.clarity.wire.s2.proto.S2DotaGcCommon;
import skadistats.clarity.wire.s2.proto.S2DotaMatchMetadata;
import skadistats.clarity.wire.s2.proto.S2NetMessages;

import java.io.IOException;

/**
 * Created by Anand on 10/8/2016.
 */
public class Attempt1 {
    private final Logger logger = LoggerFactory.getLogger(Attempt1.class.getPackage().getClass());

    @OnMessage
    public void onMessage(Context ctx, GeneratedMessage msg) {
        if (msg instanceof S1NetMessages.CSVCMsg_VoiceData || msg instanceof S2NetMessages.CSVCMsg_VoiceData) {
            return;
        }

        if (msg instanceof S2DotaMatchMetadata.CDOTAMatchMetadata) {
            logger.info("{}: {}", ctx.getTick(), "something");
        } else if (msg instanceof S2DotaMatchMetadata.CDOTAMatchMetadataFile) {
            logger.info("{}: {}", ctx.getTick(), "somethingFile");
        } else if (msg instanceof S2DotaGcCommon.CMsgDOTAMatch) {
            logger.info("{}: {}", ctx.getTick(), "match");
        } else if (msg instanceof Demo.CGameInfo.CDotaGameInfo) {
            logger.info("{}: {}", ctx.getTick(), "gameInfo");
        }

//        logger.info("{}: {}", ctx.getTick(), msg.getClass().getSimpleName());
        count++;
    }

    public void runSeek(String[] args) throws IOException {
        ControllableRunner controllableRunner = new ControllableRunner(new MappedFileSource(args[0])).runWith(this);
        controllableRunner.seek(30000);
        System.out.println("At 30000");
        controllableRunner.seek(0);
        System.out.println("At 0");
        controllableRunner.halt();
    }

    public void printInfo(String[] args) throws IOException {
        Demo.CDemoFileInfo info = Clarity.infoForFile(args[0]);
        System.out.println(info);
    }

    private int tick;
    private int count;

    @OnTickStart
    public void onTickStart(Context ctx, boolean synthetic) {
        tick = ctx.getTick();
        count = 0;
    }

    @OnTickEnd
    public void onTickEnd(Context ctx, boolean synthetic) {
        logger.info("tick {}, synthetic {}, had {} messages", tick, synthetic, count);
        if (synthetic && count > 0) {
            throw new RuntimeException("oops 1");
        }
        if (!synthetic && count == 0) {
            throw new RuntimeException("oops 2");
        }
    }

    public void run(String[] args) throws Exception {
        long tStart = System.currentTimeMillis();
        new SimpleRunner(new MappedFileSource(args[0])).runWith(this);
        long tMatch = System.currentTimeMillis() - tStart;
        logger.info("total time taken: {}s", (tMatch) / 1000.0);
    }

    public void runControlled(String[] args) throws Exception {
        long tStart = System.currentTimeMillis();
        ControllableRunner runner = new ControllableRunner(new MappedFileSource(args[0])).runWith(this);
        while(!runner.isAtEnd()) {
            runner.tick();
        }
        long tMatch = System.currentTimeMillis() - tStart;
        logger.info("total time taken: {}s", (tMatch) / 1000.0);
    }

    public static void main(String[] args) throws Exception {
        Attempt1 attempt1 = new Attempt1();
        attempt1.printInfo(args);
        attempt1.runSeek(args);
        attempt1.runControlled(args);

        // "2696382703"
    }
}
