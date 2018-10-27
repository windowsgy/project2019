package analysis.format.huawei.router;

import analysis.format.FormatInterface;

import java.util.List;

public class Version implements FormatInterface {
    @Override
    public String run(List<String> list) {
        String head1;
        StringBuilder sb = new StringBuilder();
       // int i = 0 ;
        for (String line : list) {
            if (!(line.contains("VRP (R) software, Version"))) {
                continue;
            }
         //   i++;
            head1 = line;
            String formatLine = head1+"\r\n";
            sb.append(formatLine);
        }

        /*    LogInfo.info("established row count :"+i);*/

        return sb.toString();

    }
}
