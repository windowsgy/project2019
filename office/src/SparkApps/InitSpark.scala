package SparkApps

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import utils.{Config, Log}


/**
  * Created by jlgaoyuan on 2018/11/8.
  */
object InitSpark {
  def getSession: SparkSession = {
    Log.debug("init Spark Session ")
    val sc = SparkSession.builder().master(Config.map.get("masterPath"))
      .appName(Config.map.get("appName"))
      .config("spark.driver.cores", Config.map.get("cores"))
      .config("spark.executor.memory",Config.map.get("mem"))
      .config("spark.local.dir", Config.map.get("tempPath"))
      .config("spark.sql.shuffle.partitions", Config.map.get("shuffle"))
    System.setProperty("hadoop.home.dir", Config.map.get("hadoopHome"))
    sc.getOrCreate()
  }

  def getContext: SparkContext = {
    Log.debug("init Spark Context")
    val conf = new SparkConf
    conf.setMaster(Config.map.get("masterPath")) //master路径
    conf.set("spark.app.name", Config.map.get("appName")) //应用名称
    conf.set("spark.driver.cores", Config.map.get("cores")) //cpu内核数
    conf.set("spark.executor.memory", Config.map.get("mem")) //executor设置内存大小
    conf.set("spark.local.dir", Config.map.get("tempPath")) //spark临时文件路径
    conf.set("spark.sql.shuffle.partitions", Config.map.get("shuffle")) //shuffle分区设置
    System.setProperty("hadoop.home.dir", Config.map.get("hadoopHome"))
    val sc: SparkContext = new SparkContext(conf)
    sc
  }

}
