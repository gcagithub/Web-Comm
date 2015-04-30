package ru.darout.webcom;

/**
 * This class only for test purpose.
 *
 */
public class App {
	
    public static void main(final String[] args) throws Exception {

		new AppInit().init();

//        before((request, response) -> {
//            HibernateUtil.getSession().beginTransaction();
//        });
//
//        after((request, response) -> {
//            HibernateUtil.getSession().getTransaction().commit();
//            HibernateUtil.closeSession();
//        });
//
//        exception(Exception.class, (e, request, response) -> {
//            HibernateUtil.getSession().getTransaction().rollback();
//            HibernateUtil.closeSession();
//            response.status(500);
//        });
    }
}
