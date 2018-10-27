package analysis.analysis;

import java.util.List;

public class BaseInfo implements AnalysisInterface {
    @Override
    public String run(List<List<String>> listField) {
        StringBuilder sb = new StringBuilder();
        for (List<String> list : listField) {
            String line = list.get(0) + "|" + list.get(1);
            sb.append(line).append("\r\n");
        }
        return sb.toString();
    }
}
