package SparkApps

import Config.Params
import org.apache.spark.SparkContext
import utils.Log


object Compute {
  val logger = new Log
  def run(sc: SparkContext): Unit = {
    logger.info("Compute")
    val path = Params.map.get("sourcePath")+"*"
    val rdd = ReaderGBK.transfer(sc,path)
    val rddFilter = rdd.filter(x => x.length>0 && x.contains(" "))
    // rddFilter.take(20).foreach(println)
    val rddFields = rddFilter.map(x => x.split(" ",-1)).map(x=> (x(1).trim,x(1).trim))
    val rddCount = rddFields.map(x => (x._1,1)).reduceByKey(_+_)
    println(rddCount.count())
  }
}
