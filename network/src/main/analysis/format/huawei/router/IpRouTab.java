package analysis.format.huawei.router;

import analysis.format.FormatInterface;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpRouTab implements FormatInterface {
    @Override
    public String run(List<String> list) {

        StringBuilder sb = new StringBuilder();
        try{
            // int i = 0 ;
            for (int i = 0 ; i < list.size();i++) {
                String line = list.get(i);
                if (!(includeNumber(line)&&line.contains("/"))) {//如果不包含数字和 /
                    continue;
                }
                //   i++;
                String lineFiledSize [] = line.split("\\s+");
                if(lineFiledSize.length == 6){ //等值路由缺少一条目的地址
                    //对缺少的目的地址进行补偿
                    String desip = list.get(i-1).split("\\s+")[0]+" ";//提取上一条路由的目的地址，加空格
                    line  = desip+line;
                    list.set(i,line);//修改当前行，如果出现多行空值使用
                }
                String lineArr [] = line.split("\\s+");

                String formatLine = lineArr[0]+"|"+lineArr[1]+"|"+lineArr[2]+"|"+lineArr[3]+"|"+lineArr[4]+"|"+lineArr[5]+"|"+lineArr[6]+"\r\n";
                sb.append(formatLine);
            }

              //  LogInfo.info("established row count :"+i);*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    private boolean includeNumber(String line){
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(line);
        return m.find();
    }

}
