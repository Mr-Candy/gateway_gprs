package com.gateway.messagedown.service;

import com.gateway.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//应答服务
public class ResponseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Message.class);
    private ConcurrentLinkedQueue<Message> dataQueue = new ConcurrentLinkedQueue();
    private Thread processRealDataThread;

    public ResponseService() {
        processRealDataThread = new Thread(new Runnable() {
            public void run() {
                ProcessRealDataThreadFunc();
            }
        });
        processRealDataThread.start();
    }

    public void beginAck(Message mes) {
        dataQueue.add(mes);
    }

    private void ProcessRealDataThreadFunc() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
        int times = 0;
        while (true) {
            try {
                if(times > 0 && times % 10 == 0 && dataQueue.size() > 0)
                {
                    LOGGER.error("等待应答队列数:"+dataQueue.size());
                }

                Message mes = dataQueue.poll();
                final List<Message> msgList = new ArrayList<Message>();
                while (mes != null) {
                    msgList.add(mes);
                    if (msgList.size() > 100)
                        break;
                    mes = dataQueue.poll();
                }
                if (msgList.size() > 0) {
                    fixedThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            SendGeneralAck(msgList);
                        }
                    });
                }

            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            times++;
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e1) {
            }
        }
    }

    private void SendGeneralAck(List<Message> msgList) {
        for (Message mes : msgList) {
            SendGeneralAck(mes);
        }
    }

    /**
     * 对终端发送上来的消息进行通用应答
     *
     * @param msgFromTerminal
     *            终端消息
     */
    private void SendGeneralAck(Message msgFromTerminal) {
        int msgType = msgFromTerminal.getMessageType();
        int terminalId = msgFromTerminal.getHeader().getTerminalId();

        // 终端通用应答
        if (msgType == 0x00){

        }  else {
            int ackResult = 0;// 通用应答，成功标志
            // 对于终端发送的其他命令，平台一律进行通用应答
            /*JT_8001 echoData = new JT_8001();
            echoData.setResponseMessageSerialNo(msgFromTerminal.getHeader()
                    .getMessageSerialNo());
            echoData.setResponseMessageId((short) msgType);
            echoData.setResponseResult((byte) ackResult); // 应答成功

            Message ts = new Message();
            ts.setMessageContents(echoData);
            ts.setHeader(new MessageHeader());
            ts.getHeader().setMessageType(0x8001);
            ts.getHeader().setSimId(simNo);
            ts.getHeader().setIsPackage(false);
            getMessageSender().SendMessage(ts);*/
        }

    }

       /* private MessageSender messageSender;

        public void setMessageSender(Message mes) {
            this.messageSender = messageSender;
        }

        public IMessageSender getMessageSender() {
            return messageSender;
        }

        public ICommandService getCommandService() {
            return commandService;
        }

        public void setCommandService(ICommandService commandService) {
            this.commandService = commandService;
        }

        public ITransferGpsService getTransferGpsService() {
            return transferGpsService;
        }

        public void setTransferGpsService(ITransferGpsService transferGpsService) {
            this.transferGpsService = transferGpsService;
        }*/

}
