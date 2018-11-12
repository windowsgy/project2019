package start;


import inOut.Input;
import param.Param;
import utils.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


class ControlCenter {

    static void run() {
        Log.linel1();
        Log.info("Run Control Center");
        try {
            String modelPath;
            //如果未执行当前参数初始化，则执行当前参数初始化
            if (!Param.currentParamOnOff) {
                modelPath = Param.modelTypeMap.get("CurrentParam");
            } else {//执行模块参数选择
                Input.inputParam("currentModelType",Param.modelTypeMap.keySet());
                if(!Input.inputStatus){
                    return;
                }
                //根据选择模块执行模块
                modelPath = Param.modelTypeMap.get(Param.currentModelType);
            }
            ModelInterface model = (ModelInterface)Class.forName(modelPath).newInstance();
            Method n = model.getClass().getDeclaredMethod("run");
            n.setAccessible(true);
            n.invoke(model);

        } catch (InstantiationException e) {
            Log.error("Model InstantiationException : " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.error("Model IllegalAccessException : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.error("Model  ClassNotFoundException : " + e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.error("Method  MethodNotFoundException : " + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.error(" : " + e.getMessage());
        }
    }
}
