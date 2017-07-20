// IBank.aidl
package com.ai.binder.sample;

// Declare any non-default types here with import statements

interface IBank {
        String openAccouunt(String name, String password);

        String saveMoney(int money, String account);

        String takeMoney(int money, String account, String password);

        String closeAccount(String account, String password);
}
