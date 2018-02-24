// IPersionInterface.aidl
package com.warm.aidldemo;

import com.warm.aidldemo.bean.Person;

interface IPersonInterface {

    void addPerson(in Person person);

    List<Person> getPersonList();

}
