import org.apache.spark.{SparkConf, SparkContext}

object Start {
  def main(args: Array[String]): Unit = {
    println("Start App")
    val conf = new SparkConf
    conf.setMaster(Config.masterPath) //master路径
    conf.set("spark.app.name", Config.appName) //应用名称
    conf.set("spark.driver.cores", Config.coresConf) //cpu内核数
    conf.set("spark.executor.memory", Config.memConf) //executor设置内存大小
    conf.set("spark.local.dir", Config.tmpDirPath) //spark临时文件路径
    conf.set("spark.sql.shuffle.partitions", Config.shufflePar) //shuffle分区设置
    System.setProperty("hadoop.home.dir",Config.hadoopHome )
    val sc: SparkContext = new SparkContext(conf)
    val path = "E:\\Apps\\system\\test\\format\\IpRouTab\\*"
    val rdd = sc.textFile(path)
    val rddFields = rdd.map(x => x.split("\\|"))
    val rddCount = rddFields.map(x => (x(0),1)).reduceByKey(_+_)
    rddCount.foreach(println)
  }
}
