package vending;

import org.junit.*;
import org.junit.runners.MethodSorters;
import vending.exeptions.NotSufficientChangeException;
import vending.exeptions.SoldOutException;

import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VendingMachineTest {
    private static VendingMachine vm;

    @BeforeClass
    public static void setUp() {
        vm = VendingMachineFactory.createVendingMachine();
    }

    @AfterClass
    public static void tearDown() {
        vm = null;
    }

    @Test
    public void atestBuyItemWithExactPrice() {
        long price = vm.selectItemAndGetPrice(Item.COKE);
        assertEquals(Item.COKE.getPrice(), price);
        vm.insertCoin(Coin.QUARTER);
        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();
        assertEquals(Item.COKE, item);
        assertTrue(change.isEmpty());
    }

    @Test
    public void btestBuyItemWithMorePrice() {
        long price = vm.selectItemAndGetPrice(Item.SODA);
        assertEquals(Item.SODA.getPrice(), price);

        vm.insertCoin(Coin.QUARTER);
        vm.insertCoin(Coin.QUARTER);

        Bucket<Item, List<Coin>> bucket = vm.collectItemAndChange();
        Item item = bucket.getFirst();
        List<Coin> change = bucket.getSecond();

        assertEquals(Item.SODA, item);
        assertFalse(change.isEmpty());
        assertEquals(50 - Item.SODA.getPrice(), getTotal(change));
    }

    @Test
    public void ctestRefund() {
        long price = vm.selectItemAndGetPrice(Item.PEPSI);
        assertEquals(Item.PEPSI.getPrice(), price);
        vm.insertCoin(Coin.DIME);
        vm.insertCoin(Coin.NICKLE);
        vm.insertCoin(Coin.PENNY);
        vm.insertCoin(Coin.QUARTER);

        assertEquals(41, getTotal(vm.refund()));
    }

    @Test(expected = SoldOutException.class)
    public void dtestSoldOut() {
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.COKE);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
    }

    @Test(expected = NotSufficientChangeException.class)
    public void etestNotSufficientChangeException() {
        for (int i = 0; i < 5; i++) {
            vm.selectItemAndGetPrice(Item.SODA);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();

            vm.selectItemAndGetPrice(Item.PEPSI);
            vm.insertCoin(Coin.QUARTER);
            vm.insertCoin(Coin.QUARTER);
            vm.collectItemAndChange();
        }
    }

    @Test(expected = SoldOutException.class)
    public void ftestReset() {
        VendingMachine vmachine = VendingMachineFactory.createVendingMachine();
        vmachine.reset();

        vmachine.selectItemAndGetPrice(Item.COKE);
    }

    private  long getTotal(List<Coin> change) {
        long total = 0;
        for (Coin c : change) {
            total = total + c.getDenomination();
        }
        return total;
    }
}
