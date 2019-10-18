package com.gateway.messagedown;

import com.gateway.inter.IRequest;
import com.gateway.messagedown.service.MessageDownService;
import com.gateway.tool.IntegerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MessageDownHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDownHandle.class);

    public void sendToClient(int commandType, Map<String, String> command) {
        LOGGER.debug("receive a type of " + commandType + " command");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(sdf.format(date)
                + " receive a type of "
                + IntegerUtils.Int2HexString(commandType)
                + " command :"
                + command);

        String terminalId = "";
        String flow_num = "";
        Map<String, String> commandInfo = command;

        if(commandInfo != null){
            flow_num = commandInfo.get("flow_num");
            String terminalIds = commandInfo.get("terminalId");
            String[] terminalIdArray = terminalIds.split(";");
            for(int i = 0;i < terminalIdArray.length;i++){
                terminalId = terminalIdArray[i];
                commandInfo.put("terminalId", terminalId);
                IRequest<String, String> request = getRequestProcess(commandType);
                if(request != null){
                    try{
                        byte[] bytes = request.message(commandType, commandInfo);
                        if(bytes != null){
                            MessageDownService transport = new MessageDownService(terminalId,bytes);
                            transport.sendToClient();
                        }
                    }catch (NumberFormatException e) {
                        LOGGER.debug(e.getMessage());
                    }
                }else{
                    LOGGER.debug("Unkown commandType: " + IntegerUtils.Int2HexString(commandType) + " !");
                }
            }
        }
    }

    // 获取控制令编码服务
    private IRequest<String, String> getRequestProcess(int commandType) {
        IRequest<String, String> request = null;

        if(commandType == 0x80) {//0x80 //平台通用应答
            request = new PlatformUniversalAnswerRequest();
        }
        if(commandType == 0x81) {//0x81 //设置终端参数
            System.out.println("设置终端参数");
            request = new TerminalParamsSetRequest();
        }
        if(commandType == 0x82) { //0x82 //查询终端参数
            System.out.println("查询终端参数");
            request = new TerminalParamsQueryRequest();
        }
        if(commandType == 0x83) {//0x82 //终端点名
            request = new VehiclePosInfoQueryRequest();
        }
        return request;
    }
}
