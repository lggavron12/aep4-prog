package aep;

import java.util.LinkedList;

public class JobQueue {
    private LinkedList<Integer> jobs = new LinkedList<>();
    private JobQueueListener listener;

    public JobQueue() {
        super();
    }

    public void addJobQueueListener(JobQueueListener listener) {
        this.listener = listener;
    }

    public static interface JobQueueListener {
        void jobQueueChanged(int newSize) throws InterruptedException;;
    }

    public synchronized void queueJob(int job) {
        synchronized (this) {
            this.jobs.add(job);
        }
    }

    public synchronized Integer getNextJob() throws InterruptedException{
        synchronized (this) {
            if (this.jobs.isEmpty()) {
                return 0;
            }
            Integer job = this.jobs.removeFirst();
            System.out.println("Criando um novo job");
            if (this.listener != null) {
                this.listener.jobQueueChanged(this.jobs.size());
            }
            return job;
        }
    }

}
