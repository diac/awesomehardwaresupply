package com.diac.awesomehardwaresupply.domain.enumeration;

/**
 * Перечисление привилегий пользователя в системе
 */
public enum Authority {

    /**
     * Разрешает операции чтения для базы знаний
     */
    KNOWLEDGEBASE_READ,

    /**
     * Разрешает операции записи для базы знаний
     */
    KNOWLEDGEBASE_WRITE,

    /**
     * Разрешает операции чтения для прейскуранта
     */
    PRICE_SCHEDULE_READ,

    /**
     * Разрешает операции записи для прейскуранта
     */
    PRICE_SCHEDULE_WRITE
}