package com.gateway.server;

import java.util.HashMap;

public class SessionManager {

	private static volatile SessionManager instance = null;
	// terminal num   &  session
	private HashMap<String, Session> sessionTernminalMap;

	public static SessionManager getInstance() {
		if (instance == null) {
			synchronized (SessionManager.class) {
				if (instance == null) {
					instance = new SessionManager();
				}
			}
		}
		return instance;
	}

	public SessionManager() {
		sessionTernminalMap = new HashMap<String, Session>();
	}
	
	public int getCount(){
		return sessionTernminalMap.size();
	}

	public boolean containsKey(String terminalId) {
		return sessionTernminalMap.containsKey(terminalId);
	}

	public boolean containsSession(Session session) {
		return sessionTernminalMap.containsValue(session);
	}

	public Session findSession(String terminalId) {
		return sessionTernminalMap.get(terminalId);
	}

	public synchronized void put(String terminalId, Session value) {
		sessionTernminalMap.put(terminalId, value);
	}

	public synchronized void remove(String terminalId) {
		sessionTernminalMap.remove(terminalId);
	}

}