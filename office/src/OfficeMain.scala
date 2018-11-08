
import java.util

import Config.Params
import Config.Config
import SparkApps.{Compute, InitSpark}
import utils.{FileUtils, Log}
//隐式转换java 与 scala集合类
//import collection.JavaConversions._

/**
  * Created by jlgaoyuan on 2018/11/8.
  */
object OfficeMain {
  private val logger = new Log
  def main(args: Array[String]): Unit = {
    logger.info("Start")
    val configFile = "sparkConfig.properties"
    Params.map = Config.build(configFile)
    val filesUtils = new FileUtils
    val filesPath = Params.map.get("sourcePath")
    val filesName: util.List[String] = filesUtils.getFileNameToList(filesPath)

    Compute.run(InitSpark.getContext)
  }
}
