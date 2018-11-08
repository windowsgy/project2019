package SparkApps

import org.apache.spark.SparkContext
import utils.{Config, Log}

object Compute {

  def run(sc: SparkContext): Unit = {
    Log.debug("Compute")
    val path = Config.map.get("sourcePath")+"*"
    Log.debug("load source filePath :"+path)
    val rdd = ReaderGBK.transfer(sc,path)
    val rddFilter = rdd.filter(x => x.length>0 && x.contains(" "))
    val rddFields = rddFilter.map(x => x.split(" ",-1)).map(x=> (x(1).trim,x(1).trim))
    val rddCount = rddFields.map(x => (x._1,1)).reduceByKey(_+_)
    Log.out(rddCount.count())
  }
}
