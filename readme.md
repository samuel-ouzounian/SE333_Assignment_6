![workflow](https://github.com/samuel-ouzounian/SE333_Assignment_6/actions/workflows/SE333_CI.yml/badge.svg)

## Notes:
The code for numberOfItems() in ShoppingCartAdaptor was incorrect. It was using ps.getFetchSize(), which doesn't return the number of rows in the table. It returns the fetch size which is set by JDBC. Fixed this issue. 
## Final GitHub Actions Workflow:
https://github.com/samuel-ouzounian/SE333_Assignment_6/actions/runs/13639070980

## Artifact Links:
Checkstyle: https://github.com/samuel-ouzounian/SE333_Assignment_6/actions/runs/13639070980/artifacts/2684050677
Jacoco: https://github.com/samuel-ouzounian/SE333_Assignment_6/actions/runs/13639070980/artifacts/2684051836
## Classes skipped in testing:

**Item.java**

Justification: Simple getter methods. No tests are required.