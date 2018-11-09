package analysis.analysis;


import analysis.stru.OspfPeer_Stru;
import utils.Log;

import java.util.*;

public class OspfTree  implements AnalysisInterface {

    private Set<String> treeSet = new HashSet<>();
    public String run(List<List<String>> listField){
        assert listField != null;
        List<OspfPeer_Stru> listOspfStru = buildStru(listField);
        Map<String,Integer> map = buildCountMap(listOspfStru);
        //如果包含邻居关系数量为1的ID，则一直循环
        while(map.values().contains(1)){
            //loop the map
            for( String key:map.keySet()){
                int val = map.get(key);
                if(val<= 1) {// if 邻居关系数量<1
                    treeSet.add(key);//叶子节点ID加入树集合
                    removeList(key,listOspfStru);
                }
            }
            map = buildCountMap(listOspfStru);
        }
        Log.info("Tree Set Size :"+treeSet.size());
        StringBuilder sb = new StringBuilder();
        for(String  str : treeSet) {
            sb.append(str).append("\r\n");
        }
        return sb.toString();
    }

    private List<OspfPeer_Stru> buildStru(List<List<String>> list){
        List<OspfPeer_Stru> listOspfStru  = new ArrayList<>();
        for (List<String> subList : list) {
            if (!subList.get(8).equals("Full") || !subList.contains("OSPFProcess31")) {
                continue;
            }
            OspfPeer_Stru stru = new OspfPeer_Stru();
            stru.setLocalId(subList.get(2));
            stru.setLocalIp(subList.get(4));
            stru.setRemoteId(subList.get(6));
            stru.setRemoteIp(subList.get(7));
            stru.setStatus(subList.get(8));
            listOspfStru.add(stru);
        }
        return listOspfStru;
    }

    /**
     *
     * @param list list
     * @param str  findStr
     * @return countInt
     */
    private int count (List<OspfPeer_Stru> list,String str){
        return (int) list.stream().filter(x -> x.getLocalId() .equals(str)).count();
    }

    private Map<String,Integer> buildCountMap (List<OspfPeer_Stru> listOspfStru){
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0 ;i < listOspfStru.size(); i ++){
            OspfPeer_Stru stru = listOspfStru.get(i);
            String localId = stru.getLocalId();
            if (!map.containsKey(localId)) {
                map.put(localId,count(listOspfStru,localId));
            }
        }
        return map;
    }

    /**
     * 删除list 中的结构体
     * @param removeID 删除的ID
     */
    private void removeList (String removeID,List<OspfPeer_Stru> listOspfStru){
        for(int i = 0 ; i < listOspfStru.size();i++){
            OspfPeer_Stru stru = listOspfStru.get(i);
            //如果localID  或 remoteID 等于 removeID 则在List 中删除这个节点信息
            if(stru.getRemoteId().equals(removeID)|| stru.getLocalId().equals(removeID)){
                listOspfStru.remove(i);
            }
        }
    }

}
