package ru.otus.hw07.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw07.atm.Atm;
import ru.otus.hw07.atm.AtmImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentTest {

    private DepartmentImpl department;

    private Atm atm1;
    private Atm atm2;
    private Atm atm3;

    @BeforeEach
    void setUp() {
        atm1 = AtmImpl.initState1();
        atm2 = AtmImpl.initState2();
        atm3 = AtmImpl.initState3();

        DepartmentImpl departmentImpl = new DepartmentImpl();
        departmentImpl.attach(atm1, atm2, atm3);
        this.department = departmentImpl;
    }

    @Test
    void resetIfStateIsDefined() {
        department.saveState();
        assertDoesNotThrow(() -> department.resetState());
    }

    @Test
    void resetIfAmountWasWithdrawn() {
        department.saveState();

        int balanceInState = department.getBalance();

        assertDoesNotThrow(() -> {
            atm1.withdraw(5000);
            atm2.withdraw(1000);
            atm3.withdraw(10000);
            department.resetState();
        });


        int balance = department.getBalance();

        assertEquals(balanceInState, balance);
    }

    @Test
    void balance() {
        int balance = department.getBalance();

        assertEquals(99000, balance);
    }
}