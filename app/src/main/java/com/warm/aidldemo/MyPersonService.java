package com.warm.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.warm.aidldemo.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：warm
 * 时间：2018-02-24 10:25
 * 描述：
 */
public class MyPersonService extends Service {

    private ArrayList<Person> mPerson;


    private IBinder mIBinder =new IPersonInterface.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {
            if (mPerson!=null){
                mPerson.add(person);
            }

        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            return mPerson;
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mPerson=new ArrayList<>();
        return mIBinder;
    }
}
