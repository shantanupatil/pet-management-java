package com.brady.utils;

import com.brady.service.owner.OwnerServiceRemote;
import com.brady.service.pet.PetServiceRemote;
import com.brady.service.user.UserServiceRemote;
import com.brady.service.vaccination.VaccinationServiceRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class JNDIUtil {

    public static UserServiceRemote lookupUserService() throws NamingException {
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, String.format("%s://%s:%d", "http-remoting", "localhost", 8080));
        props.put("jboss.naming.client.ejb.context", "true");
        props.put(Context.SECURITY_PRINCIPAL, "ejbuser");
        props.put(Context.SECURITY_CREDENTIALS, "ejbpassword123");
        Context context = new InitialContext(props);
        return (UserServiceRemote) context.lookup(
                "ejb:/backend-1.0-SNAPSHOT/UserService!com.brady.service.user.UserServiceRemote"
        );
    }

    public static PetServiceRemote lookupPetService() throws NamingException {
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        props.put("jboss.naming.client.ejb.context", "true");
        props.put(Context.SECURITY_PRINCIPAL, "ejbuser");
        props.put(Context.SECURITY_CREDENTIALS, "ejbpassword123");

        Context context = new InitialContext(props);
        return (PetServiceRemote) context.lookup(
                "ejb:/backend-1.0-SNAPSHOT/PetService!com.brady.service.pet.PetServiceRemote"
        );
    }

    public static OwnerServiceRemote lookupOwnerService() throws NamingException {
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        props.put("jboss.naming.client.ejb.context", "true");
        props.put(Context.SECURITY_PRINCIPAL, "ejbuser");
        props.put(Context.SECURITY_CREDENTIALS, "ejbpassword123");

        Context context = new InitialContext(props);
        return (OwnerServiceRemote) context.lookup(
                "ejb:/backend-1.0-SNAPSHOT/OwnerService!com.brady.service.owner.OwnerServiceRemote"
        );
    }

    public static VaccinationServiceRemote lookupVaccinationService() throws NamingException {
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        props.put("jboss.naming.client.ejb.context", "true");
        props.put(Context.SECURITY_PRINCIPAL, "ejbuser");
        props.put(Context.SECURITY_CREDENTIALS, "ejbpassword123");

        Context context = new InitialContext(props);
        return (VaccinationServiceRemote) context.lookup(
                "ejb:/backend-1.0-SNAPSHOT/VaccinationService!com.brady.service.vaccination.VaccinationServiceRemote"
        );
    }
}
