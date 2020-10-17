package service;

import service.impl.FindServiceImpl;

public class ServiceFactory {

    private static FindService findService;

    public static FindService getFindService(){
        if (findService == null ){
            findService = new FindServiceImpl();
        }
        return findService;
    }
}
