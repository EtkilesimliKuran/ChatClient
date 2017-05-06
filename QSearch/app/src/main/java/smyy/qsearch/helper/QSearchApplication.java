package smyy.qsearch.helper;

import android.app.Application;


import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Sümeyye on 24.4.2017.
 */

public class QSearchApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Config.baseUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
