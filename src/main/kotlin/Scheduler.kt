import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.CronTrigger
import org.quartz.Job
import org.quartz.JobBuilder.newJob
import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import org.quartz.SchedulerFactory
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory

fun startScheduler() {
    val sf: SchedulerFactory = StdSchedulerFactory()
    val scheduler = sf.scheduler

    val job: JobDetail = newJob(SendFactJob::class.java)
        .build()

    val trigger: CronTrigger = newTrigger()
        .withSchedule(cronSchedule(config.chronMaskForLaunching))
        .build()

    scheduler.scheduleJob(job, trigger)
    scheduler.start()
}

class SendFactJob : Job {
    override fun execute(context: JobExecutionContext?) {
        collectFacts().take(3).forEach {
            bot.sendMessage(chatId = config.channelId, text = "Гризли $it")
        }
    }
}