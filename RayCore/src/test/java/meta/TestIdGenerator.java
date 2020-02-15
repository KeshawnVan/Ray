//package meta;
//
//import com.star.generator.IdGenerator;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//import java.util.concurrent.*;
//
//public class TestIdGenerator {
//
//    @Test
//    public void get() {
//        IdGenerator instance = IdGenerator.getInstance();
//        Set<Long> set = new ConcurrentSkipListSet<>();
//        ExecutorService executorService = Executors.newFixedThreadPool(100);
//        List<Future> futures = new ArrayList<>(1000);
//        Runnable runnable = () -> {
//            for (int i = 0; i < 10000; i++) {
//                long id = instance.getId();
//                if (!set.add(id)) {
//                    throw new RuntimeException(id + "");
//                }
//
//            }
//        };
//        long currentTimeMillis = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            futures.add(executorService.submit(runnable));
//        }
//        futures.forEach(future -> {
//            try {
//                future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println(System.currentTimeMillis() - currentTimeMillis);
//        System.out.println(System.currentTimeMillis());
//    }
//
//    @Test
//    public void testShift() {
//        IdGenerator instance = IdGenerator.getInstance();
//        System.out.println(instance.getId());
//    }
//
//    @Test
//    public void testCal() {
//        // time is 776450557 machineId is 255 index is 2 sequence is 0
//        // 6513339358240768
//        // 6669659498556153856
//        long time = 776450557;
//        String timeBinaryString = Long.toBinaryString(time);
//        System.out.println(timeBinaryString);
//        long machineId = 255;
//        String machineIdBinaryString = Long.toBinaryString(machineId);
//        System.out.println(machineIdBinaryString);
//        String resultBinary = "1011100100011110110001111111010000000000" + "011111111" + "10" + "000000000000";
//        String trueValue = "0000000000101110010001111011000111111101" + "011111111" + "10" + "000000000000";
//        String s = "10111001000111101100011111110101111111110000000000000";
//        System.out.println(Long.valueOf(resultBinary, 2));
//        System.out.println(Long.valueOf(trueValue, 2));
//        System.out.println(Long.toBinaryString(6513339358240768L));
//    }
//}
