import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/battle-type">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Battle Type
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/battle-member">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Battle Member
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/subbranch">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Subbranch
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/current-metric">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Current Metric
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/metric">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Metric
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/resource">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Resource
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/building">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Building
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/resource-progress">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Resource Progress
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/building-process">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Building Process
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/employee">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Employee
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/winner">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Winner
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
