class Entrance implements Runnable{
    private static final Object o = new Object();
    @Override
    public void run() {
        synchronized (o) {
            while (true) {
                try {
                    Park.park.interPark();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Exit implements Runnable{
    private static final Object o = new Object();
    @Override
    public void run() {
        synchronized (o) {
            while (true) {
                try {
                    Park.park.outerPark();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}