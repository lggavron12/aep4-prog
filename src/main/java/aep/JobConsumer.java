package aep;

public class JobConsumer extends Thread {
    private JobQueue jobs;
    private Integer assignedJob = null;

    public JobConsumer(JobQueue jobs) {
        this.jobs = jobs;
    }

    @Override
    public void run() {
        while (true) {
            if (assignedJob == null) {
                try {
                    assignedJob = jobs.getNextJob();
                    if (assignedJob == null) {
                        System.out.println("Nada por enquanto " + System.currentTimeMillis() + " " + this);
                        this.sleep(4000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                for (int i = assignedJob; i >= 0; i--) {
		    System.out.println("Começou");
                    System.out.println("Tamanho do Job " + assignedJob + ", " + i + System.currentTimeMillis() + this);                    
                    try {
                        this.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                assignedJob = 0;
            }

        }
    }

}
