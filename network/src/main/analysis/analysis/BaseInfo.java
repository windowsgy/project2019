package analysis.analysis;

import java.util.List;

public class BaseInfo implements AnalysisInterface {
    @Override
    public String run(List<List<String>> listField) {
        StringBuilder sb = new StringBuilder();
        for (List<String> list : listField) {
            for(String field:list){
                sb.append(field).append("|");
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
