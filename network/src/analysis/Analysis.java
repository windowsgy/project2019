package analysis;


import analysis.analysis.AnalysisInterface;
import analysis.format.FormatInterface;
import analysis.stru.FormatStrut;
import param.Param;
import start.ModelInterface;
import base.FileUtils;
import base.ListUtils;
import base.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class Analysis implements ModelInterface {
    private FileUtils fileUtils = new FileUtils();
    private ListUtils listUtils = new ListUtils();
    @Override
    public void run() {
        Log.linel2();
        Log.info("Analysis Start");

        if (!AnalysisParam.run()) {
            return;
        }
        //创建整合路径
        Param.currentIntegratePath = Param.currentMainPath+Param.pathMap.get("integration");
        //Log.info("currentIntegratePath :"+Param.currentIntegratePath);
        if(!fileUtils.isDir(Param.currentIntegratePath)){//如果目录不存在就创建
            fileUtils.createDir(Param.currentIntegratePath);
        }

        //创建分析路径
        Param.currentAnalysisPath = Param.currentMainPath+Param.pathMap.get("analysis");
        //Log.info("currentAnalysisPath :"+Param.currentAnalysisPath);
        if(!fileUtils.isDir(Param.currentAnalysisPath)){//如果目录不存在就创建
            fileUtils.createDir(Param.currentAnalysisPath);
        }

        //返回所有格式化类型
        List<String> formatTypeList = listUtils.keyList(Param.formatMap, Param.currentCollectType);

        for(String formatType : formatTypeList) {
            //生成路径
            String formatBasePath = Param.currentMainPath+Param.pathMap.get("format");
            if(!fileUtils.isDir(formatBasePath)){
                fileUtils.createDir(formatBasePath);
            }
            String currentFormatDesPath = formatBasePath+formatType;
            if(!fileUtils.isDir(currentFormatDesPath)){//如果不存在就创建目录
                fileUtils.createDir(currentFormatDesPath);
            }else {//如果存在就删除目录中的文件
                fileUtils.deleteFiles(currentFormatDesPath);
            }
            //构建格式化结构体
            Log.info("FormatType :"+formatType);
            List<FormatStrut> listStrut = buildFormatStrut(formatType,currentFormatDesPath);
            Log.info("Build format strut size :"+listStrut.size());
            //格式化过程
            format(listStrut);
            String integratePath = integrate(listStrut,formatType);
            //加载格式化结果
            List<List<String>> integrateList = listUtils.list2ListFields(fileUtils.read2List(integratePath,0,Param.charCode),Param.splitChar);
            //返回所有分析类型
            List<String> analysisTypeList =listUtils.keyList(Param.analysisMap,formatType);
            for( String analysisType :analysisTypeList){
                Log.info("Analysis Type :"+analysisType);
                analysis(integrateList,analysisType);
            }
        }
        //如果分析完成，采集开关关闭
        Param.collectOnOff = false;
        Log.info("Analysis Finish");
        Log.linel2();
    }

    /**
     * 分析方法
     * @param list list
     * @param analysisType 分析类型
     */
    private void analysis(List<List<String>> list, String analysisType){
        String analysisFilePath = Param.currentAnalysisPath+analysisType+".txt";
       // Log.info("currentAnalysisFilePath :"+analysisFilePath);
        if(fileUtils.isFile(analysisFilePath)){//如果存在就删除
            fileUtils.delFile(analysisFilePath);
        }else {//如果不存在就创建
            fileUtils.createFile(analysisFilePath);
        }
        try {
            String result;
            //动态构造类
            String classPath = "analysis.analysis."+analysisType;
            AnalysisInterface analysis = (AnalysisInterface) Class.forName(classPath).newInstance();
            Method n = analysis.getClass().getDeclaredMethod("run", List.class);
            n.setAccessible(true);
            result = (String) n.invoke(analysis,list);//result
            if(result == null){
                Log.error("Analysis error :"+analysisType);
            }else {
                fileUtils.wrStr2File(result,analysisFilePath,Param.charCode);
              //  Log.info("Analysis Finished :"+analysisType);
            }
        }catch (Exception e){
            Log.error(e.getMessage());
            Log.error("analysis error :"+analysisType);
        }
    }


    /**
     * 格式化方法
     * @param listStrut 结构体
     */
    public  void format(List<FormatStrut> listStrut) {
        //Log.info("Format Start");
        for (FormatStrut strut: listStrut) {
            String classPath = "analysis.format" + "." + strut.getSystemType() + "." + strut.getDriversType() + "." + strut.getFormatType();
            // System.out.println("class path :"+classPath);
            FileUtils fileUtils = new FileUtils();
            //加载数据,过滤异常字符
            List<String> list = FilterList.run(fileUtils.read2List(strut.getSouPath(), 0, Param.charCode));
            String result = null;
            try {
                //动态构造类
                FormatInterface format = (FormatInterface) Class.forName(classPath).newInstance();
                Method n = format.getClass().getDeclaredMethod("run", List.class);
                n.setAccessible(true);
                result = (String) n.invoke( format,list);//result
            }catch (Exception e){
                Log.error(e.getMessage());
                Log.error("format error :"+ strut.getFileName());
                strut.setFormated(false);
            }
            if (result == null) {
                Log.error("format error :"+ strut.getFileName());
                strut.setFormated(false);
            } else {
                strut.setFormated(true);//执行格式化
                fileUtils.wrStr2File(result, strut.getDesPath(), Param.charCode);
            }
        }

        //Log.info("Format Finish");
    }


    /**
     * 整合方法
     * @param listStrut 结构体
     * @param formatType 格式化类型
     * @return string
     */
    private String integrate(List<FormatStrut> listStrut, String formatType) {
       // Log.info("Integrate Start");
        String integrateFilePath = Param.currentIntegratePath+formatType+".txt";
        //Log.info("integrateFilePath :"+integrateFilePath);
        if(fileUtils.isFile(integrateFilePath)){
            fileUtils.delFile(integrateFilePath);
        }else {
            fileUtils.createFile(integrateFilePath);
        }
        StringBuilder sb;
        sb = new StringBuilder();
        int i = 0 ;
        for (FormatStrut strut: listStrut) {
            String IpAddress = strut.getIpAddress();
            List<String> fileList = fileUtils.read2List(strut.getDesPath(),0,Param.charCode);
            for(String line: fileList) {
                if(line.length()>0){//过滤空行
                    i++;
                    sb.append(IpAddress).append("|").append(line).append("\r\n");
                }
            }
        }
        fileUtils.wrStr2File(sb.toString(),integrateFilePath,Param.charCode);
        Log.info("Integrate Line :"+i);
        //Log.info("Integrate Finish");
        return integrateFilePath;
    }


    /**
     * 构建格式化结构体
     * @param formatType 格式化类型
     * @param currentFormatDesPath 格式化后存储路径
     * @return 格式化结构体
     */
        private  List<FormatStrut> buildFormatStrut(String formatType, String currentFormatDesPath){
       // Log.info("Build FormatStrut");
        List<FormatStrut> listStrut = new ArrayList<>();
        FileUtils fileUtils = new FileUtils();
        List<String> fileNameList = fileUtils.getFilesName(Param.currentFormatSourcePath);
        for (String fileName : fileNameList) {
            FormatStrut strut = new FormatStrut();
            strut.setFileName(fileName);
            String fileNameArr[] = fileName.split(Param.fileSplitChar);
            String systemType = fileNameArr[0];
            String driversType = fileNameArr[1];
            String collectType = fileNameArr[2];
            String IpAddress = fileNameArr[3].substring(0,fileNameArr[3].lastIndexOf("."));
            strut.setSystemType(systemType);
            strut.setDriversType(driversType);
            strut.setCollectType(collectType);
            strut.setIpAddress(IpAddress);
            strut.setFormatType(formatType);
            strut.setDesPath(currentFormatDesPath+"\\"+strut.getIpAddress()+".txt");
            strut.setSouPath(Param.currentFormatSourcePath+"\\"+ fileName);
            listStrut.add(strut);
        }
        //Log.info("Build FormatStrut Finish");
        return listStrut;
    }

}

