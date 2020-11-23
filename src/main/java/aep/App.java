package aep;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog; 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import org.springframework.boot.web.embedded.netty.NettyRouteProvider;
import aula20201109.Job;
import aula20201109.AppProducerConsumer.JobProgressPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class App extends JDialog {
	private JPanel panel = new JPanel();
    private JobQueue jobs = new JobQueue();
    private List<JobConsumer> consumers = new ArrayList<>();
    private List<JobProducer> producers = new ArrayList<>();

    
    protected void createNewJob(int size) {
        Job newJob = new Job(size);
        JobProgressPanel jobProgressPanel = new JobProgressPanel(newJob);
        this.panel.add(jobProgressPanel);
        this.panel.revalidate();
        this.revalidate();
    }
    
    private static class JobProgressPanel extends JPanel {
        private Job job;
        private int wordDone = 0;
        private JProgressBar progressBar;

        public JobProgressPanel(Job job) {
            this.progressBar = new JProgressBar(job.getSize());
            this.job = job;
            BoxLayout boxLayout = new BoxLayout(this, BoxLayout.LINE_AXIS);
            this.setLayout(boxLayout);
            this.add(progressBar);
        }
    }
    
    
    public static void main(String[] args) {        
        App app = new App();
        app.setSize(400,250);
        app.setVisible(true);
    }

    public App() {
        super();
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().add(createPanel());
        
        JLabel lblAtividades = new JLabel("atividades");
        panel.add(lblAtividades);
    }


    private JPanel createPanel() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createRaisedBevelBorder());        
        final JPanel firstRowPanel = new JPanel();
        final JTextField fieldProducerCount = new JTextField(20);
        final JButton btnAddProducer = new JButton(" + ");
        btnAddProducer.addActionListener(e -> {            
        JobProducer newProducer = new JobProducer(jobs);
        producers.add(newProducer);
        fieldProducerCount.setText(String.valueOf(producers.size()));
        newProducer.start();
        });
        fieldProducerCount.setEnabled(false);
        fieldProducerCount.setMaximumSize(fieldProducerCount.getPreferredSize());
        btnAddProducer.setMaximumSize(btnAddProducer.getPreferredSize());
        
        JLabel lblNewLabel = new JLabel("Qnt de jobs:");
        final JTextField fieldJobCount = new JTextField(20);
        fieldJobCount.setEnabled(false);
        fieldJobCount.setMaximumSize(fieldJobCount.getPreferredSize());
        
        JLabel lblJobsAIncrementar = new JLabel("Jobs a incrementar");
        final JTextField fieldConsumerCount = new JTextField(20);
        fieldConsumerCount.setEnabled(false);
        fieldConsumerCount.setMaximumSize(fieldConsumerCount.getPreferredSize());
        final JButton btnAddConsumer = new JButton(" + ");
        btnAddConsumer.addActionListener(e -> {            
            JobConsumer newConsumer = new JobConsumer(jobs);
            consumers.add(newConsumer);
            fieldConsumerCount.setText(String.valueOf(consumers.size()));
            newConsumer.start();
        });
        btnAddConsumer.setMaximumSize(btnAddConsumer.getPreferredSize());
        JLabel lblUsers = new JLabel("Users: ");
        Component horizontalGlue = Box.createHorizontalGlue();
        
        final JPanel secondRowPanel = new JPanel();
        secondRowPanel.setLayout(new BoxLayout(secondRowPanel, BoxLayout.X_AXIS));
        secondRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);        

        final JPanel thirdRowPanel = new JPanel();
        thirdRowPanel.setLayout(new BoxLayout(thirdRowPanel, BoxLayout.X_AXIS));
        thirdRowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);        
        thirdRowPanel.add(Box.createHorizontalGlue());
        
        this.jobs.addJobQueueListener(jobCount -> {
           createNewJob(jobCount);
           fieldJobCount.setText(String.valueOf(jobCount)); 
        });

        panel.add(firstRowPanel);
        GroupLayout gl_firstRowPanel = new GroupLayout(firstRowPanel);
        gl_firstRowPanel.setHorizontalGroup(
        	gl_firstRowPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_firstRowPanel.createSequentialGroup()
        			.addGap(77)
        			.addComponent(lblNewLabel)
        			.addGap(5)
        			.addComponent(fieldProducerCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(5)
        			.addComponent(btnAddProducer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        		.addGroup(gl_firstRowPanel.createSequentialGroup()
        			.addGap(87)
        			.addComponent(fieldJobCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(5)
        			.addComponent(lblJobsAIncrementar))
        		.addGroup(gl_firstRowPanel.createSequentialGroup()
        			.addGap(84)
        			.addComponent(fieldConsumerCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(5)
        			.addComponent(btnAddConsumer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(5)
        			.addComponent(lblUsers)
        			.addGap(5)
        			.addComponent(horizontalGlue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        gl_firstRowPanel.setVerticalGroup(
        	gl_firstRowPanel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_firstRowPanel.createSequentialGroup()
        			.addGap(5)
        			.addGroup(gl_firstRowPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_firstRowPanel.createSequentialGroup()
        					.addGap(4)
        					.addComponent(lblNewLabel))
        				.addGroup(gl_firstRowPanel.createSequentialGroup()
        					.addGap(1)
        					.addComponent(fieldProducerCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(btnAddProducer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(5)
        			.addGroup(gl_firstRowPanel.createParallelGroup(Alignment.LEADING)
        				.addComponent(fieldJobCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_firstRowPanel.createSequentialGroup()
        					.addGap(3)
        					.addComponent(lblJobsAIncrementar)))
        			.addGap(5)
        			.addGroup(gl_firstRowPanel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_firstRowPanel.createSequentialGroup()
        					.addGap(1)
        					.addComponent(fieldConsumerCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(btnAddConsumer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_firstRowPanel.createSequentialGroup()
        					.addGap(4)
        					.addComponent(lblUsers))
        				.addGroup(gl_firstRowPanel.createSequentialGroup()
        					.addGap(10)
        					.addComponent(horizontalGlue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        );
        firstRowPanel.setLayout(gl_firstRowPanel);
        panel.add(secondRowPanel);
        panel.add(thirdRowPanel);
        return panel;
    }
}
