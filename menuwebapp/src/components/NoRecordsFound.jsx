import React from 'react';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';

function NoRecordsFound({ message, buttonTitle, onButtonClick }) {
  return (
    <Grid
      container
      spacing={3}
      direction="column"
      justify="center"
      alignItems="center"
    >
      <Grid
        item
        xs={12}
        md={8}
        lg={9}
        style={{ fontSize: 30, color: 'grey', textAlign: 'center' }}
      >
        {message}
      </Grid>
      <Grid item xs={12} md={8} lg={9}>
        <Button variant="contained" color="primary" onClick={onButtonClick}>
          {buttonTitle}
        </Button>
      </Grid>
    </Grid>
  );
}

NoRecordsFound.propTypes = {
  message: PropTypes.string.isRequired,
  buttonTitle: PropTypes.string.isRequired,
  onButtonClick: PropTypes.func.isRequired
};

export default NoRecordsFound;
