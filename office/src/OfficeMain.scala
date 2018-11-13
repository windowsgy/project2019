import java.util

import sparkApps.{Compute, InitSpark}
import javaUtils.{LoadProperties, FileUtils, Log}
import params.Params._

//隐式转换java 与 scala集合类
//import collection.JavaConversions._

/**
  * Created by jlgaoyuan on 2018/11/8.
  */
object OfficeMain {
  def main(args: Array[String]): Unit = {
    if(args.length ==1){
      if("debug" == args(0)|| "DEBUG"==args(0)){
        Log.setDebug(true)
      }
    }
    val configFile = "sparkConfig.properties"
    LoadProperties.paramMap(configFile,paramsMap)
    val filesUtils = new FileUtils
    val filesPath = paramsMap.get("sourcePath")
    val filesName: util.List[String] = filesUtils.getFilesName(filesPath)
    Log.debug("fileCount :"+filesName.size())
    Compute.run(InitSpark.getSession.sparkContext)
  }
}
