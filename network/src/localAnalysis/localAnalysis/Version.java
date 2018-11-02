package localAnalysis.localAnalysis;

import java.util.List;

public class Version implements LocalAnalysisInterface {
    /**
     *
     * @param list list
     * @return String
     */
    public String run(List<List<String>> list){
        StringBuilder sb = new StringBuilder();
        for (List<String> listFiled : list) {
            for(String field:listFiled){
                sb.append(field).append("|");
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
