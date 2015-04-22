/**
 *
 * $Id$
 */
package com.opcoach.training.rental.validation;

import com.opcoach.training.rental.Customer;

import java.util.Date;

/**
 * A sample validator interface for {@link com.opcoach.training.rental.License}.
 * This doesn't really do anything, and it's not a real EMF artifact. It was
 * generated by the org.eclipse.emf.examples.generator.validator plug-in to
 * illustrate how EMF's code generator can be extended. This can be disabled
 * with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface LicenseValidator
{
	boolean validate();

	boolean validateNumber(int value);

	boolean validateValidityDate(Date value);

	boolean validateOwner(Customer value);

	boolean validateEReference0(Customer value);
}