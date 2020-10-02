package concurrency.synchronization;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * This is a sample program demonstrating concurrency issues while booking a train
 */

class Train {
    String name;
    Integer availableSeats;

    public Train(String name, Integer noOfSeats) {
        this.name = name;
        this.availableSeats = noOfSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

}

class TicketBooking {
    Set<Train> trainList = new HashSet<>(Arrays.asList(new Train("a", 3), new Train("b", 2), new Train("c", 2)));
    ReadWriteLock reLock = new ReentrantReadWriteLock();

    public Boolean bookTicket(String trainName) {
        List<Train> trains = trainList.stream().filter(t -> t.getName().equalsIgnoreCase(trainName)).collect(Collectors.toList());
        if (trains == null) {
            return false;
        }
        Lock lock = reLock.writeLock();
        boolean res = false;
        try {
            if (trains.get(0).getAvailableSeats() > 0) {
                Integer seats = trains.get(0).getAvailableSeats();
                trains.get(0).setAvailableSeats(seats - 1);
                res = true;
            }
        } finally {
            lock.unlock();
        }
        return res;
    }
}


public class TrainTicketBookingDemo {
    public static void main(String args[]) throws Exception {
        List<String> trainNames = Arrays.asList("a", "b", "a", "c", "a", "b", "c", "a", "b");
        TicketBooking ticketBooking = new TicketBooking();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        Map<String, Object> resultMap = new HashMap<>();

        for (String trainName : trainNames) {
            Future<Boolean> future = executor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    boolean result = ticketBooking.bookTicket(trainName);
                    return result;
                }
            });
            resultMap.put(trainName, future);
        }
        waitForAll(resultMap);
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + "---> " + (Boolean) entry.getValue());
        }
    }

    private static void waitForAll(Map<String, Object> resultMap) throws Exception {
        List<Future<Boolean>> futures = resultMap.values().stream().map(future -> (Future<Boolean>) future).collect(Collectors.toList());
        while (!isAllCompleted(futures)) {
            // wait
        }
        for (String key : resultMap.keySet()) {
            Future<Boolean> future = (Future<Boolean>) resultMap.get(key);
            resultMap.put(key, future.get());
        }
    }

    private static boolean isAllCompleted(List<Future<Boolean>> futures) {
        return futures.stream().filter(future -> !future.isDone()).count() == 0;
    }
}
