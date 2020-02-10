import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import ArrowBackIcon from '@material-ui/icons/ArrowBack';

function NavButton({ previousURL, buttonTitle }) {
  return (
    <Button component={Link} to={previousURL} style={{ color: 'grey' }}>
      <ArrowBackIcon />
      <Typography
        style={{ paddingLeft: '10px', textTransform: 'none', color: 'grey' }}
        variant="h5"
      >
        {buttonTitle}
      </Typography>
    </Button>
  );
}

NavButton.propTypes = {
  previousURL: PropTypes.string.isRequired,
  buttonTitle: PropTypes.string.isRequired
};

export default NavButton;
