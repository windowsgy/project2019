package analysis.format.huawei.router;


import analysis.format.FormatInterface;

import java.util.List;

public class OspfPeer implements FormatInterface {

    @Override
    public String run( List<String> list) {

            String head1 = "";
            String head2 = "";
            String head3 = "";
            StringBuilder sb = new StringBuilder();
            int i = 0 ;
                for (String line : list) {
                    if (!(line.contains("OSPF Process") || line.contains("Area") || line.contains("Router") || line.contains("State"))) {
                        continue;
                    }
                    if (line.contains("OSPF Process")) {
                        head1 = line.replace(" with ","|");
                        continue;
                    }
                    if (line.contains("Area")) {
                        head2 = line.replace("interface","|").replace("(","|");
                        continue;
                    }
                    if (line.contains("Router")) {
                        head3 = line.replace("Address:","|");
                        continue;
                    }
                    line = line.substring(0,line.indexOf("Mode"));
                    i++;
                    String formatLine = head1+"|"+head2+"|"+head3+"|"+line+"\r\n";
                    formatLine = formatLine.replace(":","")
                            .replace("Router ID","")
                            .replace("State","")
                            .replace(")'s neighbors","")
                            .replace(" ","");
                    sb.append(formatLine);
                }

         //   LogInfo.info("OspfPeer count :"+i);

            return sb.toString();

        }
}
