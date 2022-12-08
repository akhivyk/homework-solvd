package com.solvd.transfercompany;

import com.solvd.transfercompany.order.Freighter;
import com.solvd.transfercompany.order.IdCounter;
import com.solvd.transfercompany.order.Passenger;
import com.solvd.transfercompany.people.Currency;
import com.solvd.transfercompany.people.Driver;
import com.solvd.transfercompany.people.Customer;
import com.solvd.transfercompany.people.Logistician;
import com.solvd.transfercompany.transport.Minivan;
import com.solvd.transfercompany.transport.Truck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class TransferCompany {
    private ArrayList<Logistician> allLogisticans;
    private LinkedList<Freighter> allFreighterOrders;
    private ArrayList<Driver> allDrivers;
    private ArrayList<Truck> allTrucks;
    private ArrayList<Passenger> allPassengerOrders;
    private ArrayList<Minivan> allMinivans;

    private static final Logger logger = LogManager.getLogger();

    public TransferCompany() {
        allLogisticans = new ArrayList<Logistician>();
        allFreighterOrders = new LinkedList<Freighter>();
        allPassengerOrders = new ArrayList<Passenger>();
        allDrivers = new ArrayList<Driver>();
        allTrucks = new ArrayList<Truck>();
        allMinivans = new ArrayList<Minivan>();
        this.fillDrivers();
        this.fillLogistician();
        this.fillTrucks();
        this.fillMinivans();
    }

    public ArrayList<Logistician> fillLogistician() {
        HashSet<Integer> salary = new HashSet<Integer>();
        salary.add(320);
        salary.add(430);
        salary.add(370);
        salary.add(300);
        salary.add(999);
        salary.add(435);
        Object[] asArray = salary.toArray();
        Object key = asArray[new Random().nextInt(salary.size())];
        allLogisticans.add(new Logistician("Степан", "Шарупин", 'м', (Integer) key));
        allLogisticans.add(new Logistician("Василий", "Долгинов", 'м', (Integer) key));
        allLogisticans.add(new Logistician("Ирина", "Кривошеина", 'ж', (Integer) key));
        allLogisticans.add(new Logistician("Юрий", "Шаляпин", 'м', (Integer) key));
        allLogisticans.add(new Logistician("Анастасия", "Вальховская", 'ж', (Integer) key));
        return allLogisticans;
    }

    public ArrayList<Driver> fillDrivers() {
        HashMap<String, String> drivers = new HashMap<String, String>();
        drivers.put("Александр", "Долгачев");
        drivers.put("Дмитрий", "Петрухин");
        drivers.put("Роман", "Прохоров");
        drivers.put("Денис", "Урюпов");
        Object[] array = drivers.keySet().toArray();
        Object key = array[new Random().nextInt(array.length)];
        allDrivers.add(new Driver((String) key, drivers.get(key), 'м', 'E', 550.5));
        allDrivers.add(new Driver((String) key, drivers.get(key), 'м', 'E', 550.5));
        allDrivers.add(new Driver((String) key, drivers.get(key), 'м', 'E', 550.5));
        return allDrivers;
    }

    public ArrayList<Driver> fillDrivers(Driver first, Driver second, Driver third) {
        allDrivers.add(first);
        allDrivers.add(second);
        allDrivers.add(third);
        return allDrivers;
    }

    public ArrayList<Truck> fillTrucks() {
        allTrucks.add(new Truck("Mercedes-Benz Actros MP3", 200, 4.5));
        allTrucks.add(new Truck("Scania R420", 200, 5.5));
        allTrucks.add(new Truck("MAN 18403", 180, 3.5));
        return allTrucks;
    }

    public ArrayList<Minivan> fillMinivans() {
        allMinivans.add(new Minivan("Mercedes-Benz Vito 3", 240, 7));
        allMinivans.add(new Minivan("Mercedes-Benz V-class Q230", 240, 7));
        allMinivans.add(new Minivan("Mercedes-Benz V-class W447", 240, 7));
        return allMinivans;
    }

    public LinkedList<Freighter> addFreighterOrder(Scanner in) {
        logger.info("Для создания грузовой перевозки необходимо заполнить следущие поля.");
        Freighter order = new Freighter();
        logger.info("Введите расстояние перевозки:");
        order.setDistance(Double.parseDouble(in.nextLine()));
        Random rand = new Random();
        allLogisticans.get(rand.nextInt(allLogisticans.size())).calculateCostFreighter(order);
        logger.info("Стоимость перевоза груза - " + order.getCost() + "\nДля подтверждения перевозки " +
                "введите 1. Если хотите отказаться, введите 2");
        if (Integer.parseInt(in.nextLine()) == 1) {
            order.setDriver(allDrivers.get(rand.nextInt(allDrivers.size())));
            order.setId(IdCounter.createID());
            order.setTruck(allTrucks.get(rand.nextInt(allTrucks.size())));
            allFreighterOrders.add(order);
            logger.info("Заказ на перевозку груза создан.\nУникальный идентификатор заказа: " + order.getId() + "\nДистанция перевозки груза: " + order.getDistance() + "\nВодитель: " + order.getDriver().toString() + "\nМашина: " + order.getTruck().toString());
        } else {
            logger.info("Вы отказались от перевозки.");
        }
        return allFreighterOrders;
    }

    public ArrayList<Passenger> addPassengerOrder(Scanner in) {
        logger.info("Что бы заказать трансфер людей, заполните поля.");
        Passenger order = new Passenger();
        logger.info("Введите расстояние перевозки:");
        double distance = Double.parseDouble(in.nextLine());
        order.setDistance(distance);
        logger.info("Введите количество перевозимых пассажиров:");
        if (Integer.parseInt(in.nextLine()) <= 7) {
            Random rand = new Random();
            allLogisticans.get(rand.nextInt(allLogisticans.size())).calculateCostPassenger(order);
            logger.info("Стоимость трансфера пассажиров - " + order.getCost() + "\nДля подтверждения перевозки " +
                    "введите 1. Если хотите отказаться, введите 2");
            if (Integer.parseInt(in.nextLine()) == 1) {
                order.setDriver(allDrivers.get(rand.nextInt(allDrivers.size())));
                order.setId(IdCounter.createID());
                order.setMinivan(allMinivans.get(rand.nextInt(allTrucks.size())));
                allPassengerOrders.add(order);
                logger.info("Заказ на трансфер пссажиров успешно создан.\nУникальный идентификатор заказа: " + order.getId() + "\nДистанция трансфера: " + order.getDistance() + "\nВодитель: " + order.getDriver().toString() + "\nМашина: " + order.getMinivan().toString());
            } else {
                logger.info("Вы отказались от перевозки.");
            }
        } else {
            logger.info("Приносим свои извинения, трансфер такого количества людей в нащей компании невозможен.\nКоличество перевозмых пассажиров должно быть не больше 7.");
        }

        return allPassengerOrders;
    }

    @Override
    public String toString() {
        return "TransferCompany{" +
                "\nallLogisticans=" + allLogisticans +
                "\nallFreighterOrders=" + allFreighterOrders +
                "\nallDrivers=" + allDrivers +
                "\nallTrucks=" + allTrucks +
                "\nallPassengerOrders=" + allPassengerOrders +
                "\nallMinivans=" + allMinivans +
                '}';
    }

    public LinkedList<Freighter> getAllFreighterOrders() {
        return allFreighterOrders;
    }

    public void setAllFreighterOrders(LinkedList<Freighter> allFreighterOrders) {
        this.allFreighterOrders = allFreighterOrders;
    }

    public ArrayList<Logistician> getAllLogisticans() {
        return allLogisticans;
    }

    public void setAllLogisticans(ArrayList<Logistician> allLogisticans) {
        this.allLogisticans = allLogisticans;
    }

    public ArrayList<Passenger> getAllPassengerOrders() {
        return allPassengerOrders;
    }

    public void setAllPassengerOrders(ArrayList<Passenger> allPassengerOrders) {
        this.allPassengerOrders = allPassengerOrders;
    }

    public ArrayList<Driver> getAllDrivers() {
        return allDrivers;
    }

    public void setAllDrivers(ArrayList<Driver> allDrivers) {
        this.allDrivers = allDrivers;
    }

    public ArrayList<Truck> getAllTrucks() {
        return allTrucks;
    }

    public void setAllTrucks(ArrayList<Truck> allTrucks) {
        this.allTrucks = allTrucks;
    }

    public ArrayList<Minivan> getAllMinivans() {
        return allMinivans;
    }

    public void setAllMinivans(ArrayList<Minivan> allMinivans) {
        this.allMinivans = allMinivans;
    }
}
