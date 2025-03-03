![workflow](https://github.com/samuel-ouzounian/SE333_Assignment_6/actions/workflows/SE333_CI.yml/badge.svg)

## Notes:
The code for numberOfItems() in ShoppingCartAdaptor was incorrect. It was using ps.getFetchSize(), which doesn't return the number of rows in the table. It returns the fetch size which is set by JDBC. Fixed this issue. 
## Final GitHub Actions Workflow:

## Artifact Links:

## Classes skipped in testing:

**Item**
Justification: Simple getter methods. No tests are required.