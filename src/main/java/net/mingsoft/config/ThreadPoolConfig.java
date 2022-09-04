package net.mingsoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

// @Configuration
//@EnableAsync
public class ThreadPoolConfig {

    /**
     *   默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
     *  当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     *  当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝
     */

    /**
     * 获得Java虚拟机可用的处理器个数 + 1
     */
    private static final int THREADS = Runtime.getRuntime().availableProcessors() + 1;

    //    @Value("${async.executor.thread.core_pool_size:0}") //可在配置文件配置核心线程数
    public static int corePoolSizeConfig = 0;
    // 核心线程池大小
    public static int coreIoPoolSize = (corePoolSizeConfig == 0 ? THREADS : corePoolSizeConfig) * 4;

    // 最大可创建的线程数
    //@Value("${async.executor.thread.max_pool_size}")
    private int maxIoPoolSize = 4 * 2 * THREADS;

    // 核心线程池大小
    public static int coreCpuPoolSize = corePoolSizeConfig == 0 ? THREADS : corePoolSizeConfig;

    // 最大可创建的线程数
    //@Value("${async.executor.thread.max_pool_size}")
    private int maxCpuPoolSize = 2 * THREADS;


    // 队列最大长度
    //@Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity = 1024;

    // 线程池维护线程所允许的空闲时间
    //@Value("${async.executor.thread.keep_alive_seconds}")
    private int keepAliveSeconds = 60;


    //线程池名前缀
    //@Value("${async.executor.thread.threadNamePrefix}")
    private static final String threadNamePrefix = "Async-Service-";

    /**
     * IO 密集类型线程池 （corePoolSize 核心线程 和 maxPoolSize最大线程数比cpu核数翻4倍）
     *
     * @return
     */
    @Bean(name = "threadIoPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadIoPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxIoPoolSize);
        executor.setCorePoolSize(coreIoPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务(无线程可用)的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

    /**
     * cpu 密集类型线程池
     *
     * @return
     */
    @Bean(name = "threadCpuPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadCpuPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxCpuPoolSize);
        executor.setCorePoolSize(coreCpuPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务(无线程可用)的处理策略
        // CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }


}



