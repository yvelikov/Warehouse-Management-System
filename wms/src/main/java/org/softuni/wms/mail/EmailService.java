package org.softuni.wms.mail;

import java.util.concurrent.Future;

public interface EmailService {

    Future<Boolean> sendSimpleMessage(Mail mail);
}
