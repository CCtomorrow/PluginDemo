/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/qingyong/Dev/Projects/Practice/PluginDemo/binder_sample/src/main/aidl/com/ai/binder/sample/IBank.aidl
 */
package com.ai.binder.sample.gen;
// Declare any non-default types here with import statements

public interface IBank extends android.os.IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements IBank {
        private static final String DESCRIPTOR = "com.ai.binder.sample.IBank";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.ai.binder.sample.IBank interface,
         * generating a proxy if needed.
         */
        public static IBank asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof IBank))) {
                return ((IBank) iin);
            }
            return new Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_openAccouunt: {
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0;
                    _arg0 = data.readString();
                    String _arg1;
                    _arg1 = data.readString();
                    String _result = this.openAccouunt(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_saveMoney: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    String _arg1;
                    _arg1 = data.readString();
                    String _result = this.saveMoney(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_takeMoney: {
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0;
                    _arg0 = data.readInt();
                    String _arg1;
                    _arg1 = data.readString();
                    String _arg2;
                    _arg2 = data.readString();
                    String _result = this.takeMoney(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
                case TRANSACTION_closeAccount: {
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0;
                    _arg0 = data.readString();
                    String _arg1;
                    _arg1 = data.readString();
                    String _result = this.closeAccount(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IBank {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public String openAccouunt(String name, String password) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(password);
                    mRemote.transact(Stub.TRANSACTION_openAccouunt, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String saveMoney(int money, String account) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(money);
                    _data.writeString(account);
                    mRemote.transact(Stub.TRANSACTION_saveMoney, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String takeMoney(int money, String account, String password) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(money);
                    _data.writeString(account);
                    _data.writeString(password);
                    mRemote.transact(Stub.TRANSACTION_takeMoney, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String closeAccount(String account, String password) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(account);
                    _data.writeString(password);
                    mRemote.transact(Stub.TRANSACTION_closeAccount, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }

        static final int TRANSACTION_openAccouunt = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_saveMoney = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_takeMoney = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_closeAccount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    }

    public String openAccouunt(String name, String password) throws android.os.RemoteException;

    public String saveMoney(int money, String account) throws android.os.RemoteException;

    public String takeMoney(int money, String account, String password) throws android.os.RemoteException;

    public String closeAccount(String account, String password) throws android.os.RemoteException;
}
