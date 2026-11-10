import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        int routesCount = 1000;
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < routesCount; i++) {
            Thread thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                
                int countR = 0;
                for (char c : route.toCharArray()) {
                    if (c == 'R') {
                        countR++;
                    }
                }

                synchronized (sizeToFreq) {
                    sizeToFreq.put(countR, sizeToFreq.getOrDefault(countR, 0) + 1);
                }
            });
        
            threads.add(thread);
            thread.start();
        }


        for (Thread thread : threads) {
            thread.join();
        }

        printResults();
        
    }

    private static void printResults() {
        int maxFreq = 0;
        int mostFrequentSize = 0;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxFreq) {
                maxFreq = entry.getValue();
                mostFrequentSize = entry.getKey();
            }
        }

        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n", mostFrequentSize, maxFreq);
        
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != mostFrequentSize) {
                System.out.printf("- %d (%d раз)\n", entry.getKey(), entry.getValue());
            }
        }
    
    }

}
