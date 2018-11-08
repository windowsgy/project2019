package SparkApps

import Config.Params
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import utils.Log

/**
  * Created by jlgaoyuan on 2018/11/8.
  */
object InitSpark {
  private val logger = new Log
  def getSession: SparkSession = {
    logger.info("init Spark Session ")
    val sc = SparkSession.builder().master(Params.map.get("masterPath"))
      .appName(Params.map.get("appName"))
      .config("spark.driver.cores", Params.map.get("cores"))
      .config("spark.executor.memory", Params.map.get("mem"))
      .config("spark.local.dir", Params.map.get("tempPath"))
      .config("spark.sql.shuffle.partitions", Params.map.get("shuffle"))
    System.setProperty("hadoop.home.dir", Params.map.get("hadoopHome"))
    sc.getOrCreate()
  }

  def getContext: SparkContext = {
    logger.info("init Spark Context")
    val conf = new SparkConf
    conf.setMaster(Params.map.get("masterPath")) //master路径
    conf.set("spark.app.name", Params.map.get("appName")) //应用名称
    conf.set("spark.driver.cores", Params.map.get("cores")) //cpu内核数
    conf.set("spark.executor.memory", Params.map.get("mem")) //executor设置内存大小
    conf.set("spark.local.dir", Params.map.get("tempPath")) //spark临时文件路径
    conf.set("spark.sql.shuffle.partitions", Params.map.get("shuffle")) //shuffle分区设置
    System.setProperty("hadoop.home.dir", Params.map.get("hadoopHome"))
    val sc: SparkContext = new SparkContext(conf)
    sc
  }

}
