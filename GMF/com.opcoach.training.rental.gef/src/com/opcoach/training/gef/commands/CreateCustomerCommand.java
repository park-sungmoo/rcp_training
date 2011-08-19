package com.opcoach.training.gef.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;

public class CreateCustomerCommand extends Command
{
	private Customer customer = null;
	private RentalAgency agency = null;
	private Rectangle customerBox = null;

	public CreateCustomerCommand(Customer c, RentalAgency parentAgency, Rectangle constraint)
	{
		super("Create Customer");
		agency = parentAgency;
		customer = c;
		customerBox = constraint;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute()
	{
		System.out.println("Enter in Create Customer");
		redo();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo()
	{
		// Add customer in agency.
		agency.addCustomer(customer);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo()
	{
		agency.removeCustomer(customer);
	}

}