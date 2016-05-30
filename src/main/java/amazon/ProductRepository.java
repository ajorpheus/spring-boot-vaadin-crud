/*
 *
 *  * Copyright 2003-2015 Monitise Group Limited. All Rights Reserved.
 *  *
 *  * Save to the extent permitted by law, you may not use, copy, modify,
 *  * distribute or create derivative works of this material or any part
 *  * of it without the prior written consent of Monitise Group Limited.
 *  * Any reproduction of this material must contain this notice.
 *
 */

package amazon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by az on 27/05/2016.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameStartsWithIgnoreCase(String lastName);
}
