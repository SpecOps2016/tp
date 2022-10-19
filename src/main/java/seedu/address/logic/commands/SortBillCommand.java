package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.bill.Bill;

/**
 * Sorts Bill data in Address Book.
 */
public class SortBillCommand extends Command {
    public static final CommandWord COMMAND_WORD = new CommandWord("sortbill");
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Sorts the list of bills according to the specified field."
                    + "by alphabetical order.\n"
                    + "Existing remark will be overwritten by the input.\n"
                    + "Parameters: FIELD (must not be empty) "
                    + "Example: " + COMMAND_WORD + "name";

    public static final String MESSAGE_SORT_SUCCESS = "Sorted according to %1$s";

    private final String criteria;
    private final boolean isAscending;

    /**
     * @param criteria to be sorted by
     */
    public SortBillCommand(String criteria, boolean isAscending) {
        requireAllNonNull(criteria);
        this.criteria = criteria;
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (this.criteria.toLowerCase().equals("appointment")) {
            AppointmentComparator appointmentComparator = new AppointmentComparator();
            model.sortBills(appointmentComparator, this.isAscending);
        } else if (this.criteria.toLowerCase().equals("amount")) {
            AmountComparator amountComparator = new AmountComparator();
            model.sortBills(amountComparator, this.isAscending);
        } else if (this.criteria.toLowerCase().equals("date")) {
            DateComparator dateComparator = new DateComparator();
            model.sortBills(dateComparator, this.isAscending);
        } else if (this.criteria.toLowerCase().equals("status")) {
            StatusComparator statusComparator = new StatusComparator();
            model.sortBills(statusComparator, this.isAscending);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_SORT_CRITERIA);
        }

        String message = String.format(MESSAGE_SORT_SUCCESS, this.criteria);
        return new CommandResult(message);
    }

    /**
     * Compares two Bills by appointment.
     */
    public class AppointmentComparator implements Comparator<Bill> {
        @Override
        public int compare(Bill first, Bill second) {
            return first.getAppointment().toString().compareTo(second.getAppointment().toString());
        }
    }

    /**
     * Compares two Bills by amount.
     */
    public class AmountComparator implements Comparator<Bill> {
        @Override
        public int compare(Bill first, Bill second) {
            return first.getAmount().toString().compareTo(second.getAmount().toString());
        }
    }

    /**
     * Compares two Bills by date.
     */
    public class DateComparator implements Comparator<Bill> {
        @Override
        public int compare(Bill first, Bill second) {
            return first.getBillDate().toString().compareTo(second.getBillDate().toString());
        }
    }

    /**
     * Compares two Bills by payment status.
     */
    public class StatusComparator implements Comparator<Bill> {
        @Override
        public int compare(Bill first, Bill second) {
            return first.getPaymentStatus().toString().compareTo(second.getPaymentStatus().toString());
        }
    }
}